import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { IModalData } from '../main/products';
import { ProductsService } from '../../../../services/productsService';
import { IProduct } from '../../../../interfaces/IProduct';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { ICategory } from '../../../../interfaces/ICategory';
import { CategoriesService } from '../../../../services/categoriesService';
import { Subscription } from 'rxjs';
import { refreshCategoriesObservable } from '../../../../misc/observables';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-create-edit-product',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, FormsModule, MatDialogModule, MatSelectModule, CommonModule],
  templateUrl: './create-edit-product.html',
  styleUrl: './create-edit-product.scss'
})
export class CreateEditProduct implements OnInit {

  public id: string | null = null;
  public action: 'Create' | 'Save' = 'Create';
  public name: string = '';
  public categoryId: string = '';

  public categories: ICategory[] = [];
  private subscription: Subscription | null = null;

  constructor(private categoriesService: CategoriesService, private productsService: ProductsService, private editCreateModal: MatDialogRef<CreateEditProduct>, @Inject(MAT_DIALOG_DATA) private modalData: IModalData) {}

  private async loadCategories(): Promise<void> {
    const _categories: ICategory[] = await this.categoriesService.getAll();
    this.categories = _categories;
  }

  public ngOnInit(): void {
   
    this.loadCategories();
    this.subscription = refreshCategoriesObservable.subscribe(() => this.loadCategories());

    if (this.modalData && this.modalData.product) {
        this.action = 'Save';
        this.name = this.modalData.product.name || '';
        this.categoryId = this.modalData.product.category.id || '';
        this.id = this.modalData.product.id || null;
    } else {
        this.action = 'Create';
        this.id = null;
        this.name = '';
        this.categoryId = '';
    }
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  public async saveProduct(): Promise<void> {
    // if (!this.name.trim() || !this.categoryId.trim()) {
    //   console.error('Product name and category cannot be empty');
    //   return;
    // }

    if (this.action === 'Create') {
      await this.productsService.create({ name: this.name, category: { id: this.categoryId } } as IProduct);
    }

    if (this.action === 'Save') {
      await this.productsService.update({ id: this.id, name: this.name, category: { id: this.categoryId } } as IProduct);
    }

    this.editCreateModal.close(this.name);
  }
}
