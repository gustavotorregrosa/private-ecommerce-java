import { AfterViewInit, Component, inject, OnDestroy, OnInit } from '@angular/core';
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
import { refreshMovimentationsObservable } from '../../../../misc/observables';
import { Subscription } from 'rxjs';


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
export class Movimentations implements OnInit, AfterViewInit, OnDestroy {

  private productId: string | null = null;
  private chart: Chart | null = null;
  private ctx: HTMLCanvasElement | null = null;
  private stockPositions: IStockPosition[] = [];
  private subscription: Subscription | null = null;

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
     this.subscription = refreshMovimentationsObservable.subscribe((changedProductID: string) => {
  
      if (this.productId === changedProductID) {
        this.loadStockPositions();
      }
    });
  }


  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
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
            beginAtZero: true,
          },
        }
      }
    });
  }

  private async loadStockPositions(): Promise<void> {
    if (this.productId) {
      try {
        this.stockPositions = await this.movimentationsService.getStockPositionsByProductId(this.productId);

        this.chart!.data.labels = this.stockPositions.map(m => m.createdAt);
        this.chart!.data.datasets[0].data = this.stockPositions.map(m => m.amount);

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
