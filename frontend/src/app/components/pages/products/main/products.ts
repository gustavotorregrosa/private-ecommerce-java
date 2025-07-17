import { Component, OnDestroy, OnInit } from '@angular/core';
import { ProductsService } from '../../../../services/productsService';
import { IProduct } from '../../../../interfaces/IProduct';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { CreateEditProduct } from '../create-edit-product/create-edit-product';
import { DeleteProduct } from '../delete-product/delete-product';
// import { refreshProductsObservable } from '../../../../misc/observables';
import { Subscription } from 'rxjs';

export interface IModalData {
  action: 'create' | 'edit';
  product?: IProduct;
}

@Component({
  selector: 'app-products',
  imports: [MatTableModule, MatIconModule, MatButtonModule, MatCardModule],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products implements OnInit, OnDestroy {

  public products: IProduct[] = [];
  displayedColumns: string[] = ['name', 'category', 'actions'];
  private subscription: Subscription | null = null;

  constructor(private productsService: ProductsService, private dialog: MatDialog) {}

  ngOnInit(): void {
    // this.loadProducts();
    // this.subscription = refreshProductsObservable.subscribe(() => this.loadProducts());
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  public openAddModal(): void {
    const dialogCreateEditRef = this.dialog.open(CreateEditProduct, {
      width: '400px',
      data: { action: 'create' } as IModalData
    });
  }

  public openEditModal(product: IProduct): void {
    const dialogCreateEditRef = this.dialog.open(CreateEditProduct, {
      width: '400px',
      data: { action: 'edit', product } as IModalData
    });
  }

  private async loadProducts(): Promise<void> {
    this.products = await this.productsService.getAll();
  }

  public openDeleteModal(product: IProduct): void {
    const dialogDeleteRef = this.dialog.open(DeleteProduct, {
      width: '400px',
      data: { product } as IModalData
    });
  }
}
