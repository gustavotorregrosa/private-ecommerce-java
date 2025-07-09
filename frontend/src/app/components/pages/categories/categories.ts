import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../../services/httpService';
import { ICategory } from '../../../interfaces/ICategory';
import { IResponse } from '../../../interfaces/IResponse';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { CreateEditCategory } from './create-edit-category/create-edit-category';

@Component({
  selector: 'app-categories',
  imports: [MatTableModule, MatIconModule, MatButtonModule, MatCardModule],
  templateUrl: './categories.html',
  styleUrl: './categories.scss'
})
export class Categories implements OnInit {

  public categories: ICategory[] = [];
  displayedColumns: string[] = ['name', 'actions']; 

  constructor(private httpService: HttpService,  private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  public openAddModal(): void {
    const dialogRef = this.dialog.open(CreateEditCategory, {
      width: '400px',
      data: { action: 'add' }
    });

    // dialogRef.afterClosed().subscribe(result => {
    //   if (result) {
    //     this.loadCategories(); // Reload categories after adding a new one
    //   }
    // });
  }

  private async loadCategories(): Promise<void> {
    const _categories: ICategory[] = (await this.httpService.get<IResponse<ICategory[]>>('/categories')).data
    this.categories = _categories;

  }

  public async doDelete(category: ICategory): Promise<void> {
    console.log('Deleting category:', category);
  }

  public async doEdit(category: ICategory): Promise<void> {
    console.log('Editing category:', category);
  }

}
