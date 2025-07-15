import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { IModalData } from '../main/products';
import { MatButtonModule } from '@angular/material/button';
import { ProductsService } from '../../../../services/productsService';

@Component({
  selector: 'app-delete-product',
  imports: [MatButtonModule],
  templateUrl: './delete-product.html',
  styleUrl: './delete-product.scss'
})
export class DeleteProduct implements OnInit {

  public id: string | null = null;
  public name: string = '';

  constructor(private deleteModal: MatDialogRef<DeleteProduct>, @Inject(MAT_DIALOG_DATA) private modalData: IModalData, private productsService: ProductsService) {}

  public ngOnInit(): void {
    if (this.modalData && this.modalData.product) {
      this.name = this.modalData.product.name || '';
      this.id = this.modalData.product.id || null;
    }
  }

  public async deleteProduct(): Promise<void> {
    if (this.id) {
      await this.productsService.delete(this.id);
      this.deleteModal.close(this.id);
    } else {
      console.error('No product ID provided for deletion');
    }
  }
}
