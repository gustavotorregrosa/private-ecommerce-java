import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../../../services/httpService';
import { ICategory } from '../../../../interfaces/ICategory';
import { IResponse } from '../../../../interfaces/IResponse';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { CreateEditCategory } from '../create-edit-category/create-edit-category';
import { DeleteCategory } from '../delete-category/delete-category';

export interface IModalData {
  action: 'create' | 'edit';
  category?: ICategory;
}

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
    const dialogCreateEditRef = this.dialog.open(CreateEditCategory, {
      width: '400px',
      data: { action: 'create' } as IModalData
    });
  }

  public openEditModal(category: ICategory): void {
    const dialogCreateEditRef = this.dialog.open(CreateEditCategory, {
      width: '400px',
      data: { action: 'edit', category } as IModalData
    });
  }

  private async loadCategories(): Promise<void> {
    const _categories: ICategory[] = (await this.httpService.get<IResponse<ICategory[]>>('/categories')).data
    this.categories = _categories;

  }

  public openDeleteModal(category: ICategory): void {
    console.log('Deleting category:', category);
    const dialogDeleteRef = this.dialog.open(DeleteCategory, {
      width: '400px',
      data: { category } as IModalData
    });
  }

}
