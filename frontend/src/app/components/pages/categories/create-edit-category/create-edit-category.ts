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

    public ngOnInit(): void {
        // Initialize any necessary data or state here
        console.log('CreateEditCategory initialized with modal data:', this.modalData);
    }

    public name: string = '';

    constructor(private editCreateModal: MatDialogRef<CreateEditCategory>, @Inject(MAT_DIALOG_DATA) private modalData: IModalData) {}

    public saveCategory(): void {
      console.log('Category saved:', this.name);
      console.log('Modal data:', this.modalData);
      this.editCreateModal.close(this.name); // Close the dialog and pass the name back
    }

}
