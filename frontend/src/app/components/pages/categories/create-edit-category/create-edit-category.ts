import { Component, Inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { IModalData } from '../main/categories';
import { CategoriesService } from '../../../../services/categoriesService';
import { ICategory } from '../../../../interfaces/ICategory';

@Component({
  selector: 'app-create-edit-category',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, FormsModule, MatDialogModule],
  templateUrl: './create-edit-category.html',
  styleUrl: './create-edit-category.scss'
})
export class CreateEditCategory implements OnInit {

    public id: string | null = null;
    public action: 'Create' | 'Save' = 'Create';
    public name: string = '';
 
    constructor(private categoriesService: CategoriesService,  private editCreateModal: MatDialogRef<CreateEditCategory>, @Inject(MAT_DIALOG_DATA) private modalData: IModalData) {}
    
    public ngOnInit(): void {
        if (this.modalData && this.modalData.category) {
            this.action = 'Save';
            this.name = this.modalData.category.name || '';
            this.id = this.modalData.category.id || null;
        } else {
            this.action = 'Create';
            this.id = null;
            this.name = '';
        }
    }

    public async saveCategory(): Promise<void> {
      if (!this.name.trim()) {
        return;
      }

      if(this.action === 'Create') {
        await this.categoriesService.create({ name: this.name } as ICategory);
      }

      if(this.action === 'Save') {
        await this.categoriesService.update({ id: this.id, name: this.name } as ICategory);
      }

      this.editCreateModal.close(this.name);
      
    }

}
