import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { IModalData } from '../main/categories';
import { MatButtonModule } from '@angular/material/button';
import { CategoriesService } from '../../../../services/categoriesService';

@Component({
  selector: 'app-delete-category',
  imports: [MatButtonModule],
  templateUrl: './delete-category.html',
  styleUrl: './delete-category.scss'
})
export class DeleteCategory implements OnInit {

    public id: string | null = null;
    public name: string = '';

    constructor(private deleteModal: MatDialogRef<DeleteCategory> , @Inject(MAT_DIALOG_DATA) private modalData: IModalData, private categoriesService: CategoriesService) {}

    public ngOnInit(): void {
        if (this.modalData && this.modalData.category) {
            this.name = this.modalData.category.name || '';
            this.id = this.modalData.category.id || null;
        }
    }

    public async deleteCategory(): Promise<void> {
        if (this.id) {
            console.log(`Deleting category with ID: ${this.id}`);
            await this.categoriesService.delete(this.id);
            this.deleteModal.close(this.id);
        } else {
            console.error('No category ID provided for deletion');
        }
    }

}
