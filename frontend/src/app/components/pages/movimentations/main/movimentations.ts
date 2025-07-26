import { Component, inject, OnInit } from '@angular/core';
import { HttpService } from '../../../../services/httpService';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ProductsService } from '../../../../services/productsService';

@Component({
  selector: 'app-movimentations',
  imports: [],
  templateUrl: './movimentations.html',
  styleUrl: './movimentations.scss'
})
export class Movimentations implements OnInit {

  private productId: string | null = null;

  

  constructor(private httpService: HttpService, private dialog: MatDialog, private route: ActivatedRoute, private productsService: ProductsService) {}


  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
    console.log('Product ID:', this.productId); // Debugging line to check productId
    // Initialize component logic here
    // For example, you might want to fetch movimentations for the product
    // this.loadMovimentations();
  }

  

}
