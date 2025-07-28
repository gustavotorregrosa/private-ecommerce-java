import { AfterViewInit, Component, inject, OnInit } from '@angular/core';
import { HttpService } from '../../../../services/httpService';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ProductsService } from '../../../../services/productsService';
import { Chart, registerables } from 'chart.js';
import { MovimentationsService } from '../../../../services/movimentationsService';
import { IStockPosition } from '../../../../interfaces/IStockPosition';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AddMovimentationModal } from '../add-movimentation/add-movimentation';


export interface IModalData {
  action: 'add' | 'withdraw';
  productId: string;
}

@Component({
  selector: 'app-movimentations',
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './movimentations.html',
  styleUrl: './movimentations.scss'
})
export class Movimentations implements OnInit, AfterViewInit {

  private productId: string | null = null;
  private chart: Chart | null = null;
  private ctx: HTMLCanvasElement | null = null;
  private stockPositions: IStockPosition[] = [];

  public getStockPositions(): IStockPosition[] {
    return this.stockPositions;
  }

  constructor(private movimentationsService: MovimentationsService, private httpService: HttpService, private dialog: MatDialog, private route: ActivatedRoute, private productsService: ProductsService) {}

  ngAfterViewInit(): void {
    this.registerChart();
    this.loadStockPositions()
  }

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
    console.log('Product ID:', this.productId);
  }


    public openAddMovimentationModal(action: 'add' | 'withdraw'): void {
      const dialogMovimentationRef = this.dialog.open(AddMovimentationModal, {
        width: '400px',
        data: { action, productId: this.productId } as IModalData
      });
    }

  private registerChart(): void {
    Chart.register(...registerables);
    this.ctx = document.getElementById('chartContainer') as HTMLCanvasElement;

    this.chart = new Chart(this.ctx, {
      type: 'line',
      data: {
        labels: [],
        datasets: [{
          label: 'Movimentations',
          data: [],
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 3
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  private async loadStockPositions(): Promise<void> {
    if (this.productId) {
      try {
        this.stockPositions = await this.movimentationsService.getStockPositionsByProductId(this.productId);

        this.chart!.data.labels = this.stockPositions.map(m => m.date.toISOString().split('T')[0]);
        this.chart!.data.datasets[0].data = this.stockPositions.map(m => m.amout);

        this.chart!.update()
        console.log('Loaded stock positions:', this.stockPositions);
      } catch (error) {
        console.error('Error loading stock positions:', error);
      }
    } else {
      console.warn('No product ID provided to load stock positions.');
    }
  }


}
