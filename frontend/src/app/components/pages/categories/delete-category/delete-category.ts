import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IModalData } from '../main/categories';

@Component({
  selector: 'app-delete-category',
  imports: [],
  templateUrl: './delete-category.html',
  styleUrl: './delete-category.scss'
})
export class DeleteCategory implements OnInit {

    public id: string | null = null;
    public name: string = '';

    constructor(@Inject(MAT_DIALOG_DATA) private modalData: IModalData) {}

    public ngOnInit(): void {
        if (this.modalData && this.modalData.category) {
            this.name = this.modalData.category.name || '';
            this.id = this.modalData.category.id || null;
        }
    }

    public deleteCategory(): void {
        if (this.id) {
            console.log(`Deleting category with ID: ${this.id}`);
            // Here you would typically call a service to delete the category
            // this.categoriesService.delete(this.id);
        } else {
            console.error('No category ID provided for deletion');
        }
    }

}
