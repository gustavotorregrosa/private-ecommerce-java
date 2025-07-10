import { Component, Inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { IModalData } from '../categories';

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

    constructor(private editCreateModal: MatDialogRef<CreateEditCategory>, @Inject(MAT_DIALOG_DATA) private modalData: IModalData) {}
    
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

    public saveCategory(): void {
      if (!this.name.trim()) {
        console.error('Category name cannot be empty');
        return;
      }

      console.log('Category saved:', this.name);
      console.log('Modal data:', this.modalData);

      this.editCreateModal.close(this.name); // Close the dialog and pass the name back
    }

}
