import { AfterViewInit, Component, inject, OnInit } from '@angular/core';
import { HttpService } from '../../../../services/httpService';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ProductsService } from '../../../../services/productsService';
import { Chart, registerables } from 'chart.js';
import { MovimentationsService } from '../../../../services/movimentationsService';
import { IMovimentation } from '../../../../interfaces/IMovimentation';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

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
  private movimentations: IMovimentation[] = [];

  public getMovimentations(): IMovimentation[] {
    return this.movimentations;
  }

  constructor(private movimentationsService: MovimentationsService, private httpService: HttpService, private dialog: MatDialog, private route: ActivatedRoute, private productsService: ProductsService) {}

  ngAfterViewInit(): void {
    this.registerChart();
    this.loadMovimentations()
  }

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
    console.log('Product ID:', this.productId);
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

  private async loadMovimentations(): Promise<void> {
    if (this.productId) {
      try {
        this.movimentations = await this.movimentationsService.getMovimentationsByProductId(this.productId);

        this.chart!.data.labels = this.movimentations.map(m => m.date.toISOString().split('T')[0]);
        this.chart!.data.datasets[0].data = this.movimentations.map(m => m.amout);

        this.chart!.update()
        console.log('Loaded movimentations:', this.movimentations); // Debugging line to check loaded movimentations
      } catch (error) {
        console.error('Error loading movimentations:', error);
      }
    } else {
      console.warn('No product ID provided to load movimentations.');
    }
  }


}
