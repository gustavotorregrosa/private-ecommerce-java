import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../../services/httpService';
import { ICategory } from '../../../interfaces/ICategory';
import { IResponse } from '../../../interfaces/IResponse';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-categories',
  imports: [MatTableModule, MatIconModule, MatButtonModule],
  templateUrl: './categories.html',
  styleUrl: './categories.scss'
})
export class Categories implements OnInit {

  public categories: ICategory[] = [];
  displayedColumns: string[] = ['name', 'actions'];

  constructor(private httpService: HttpService) {}

  ngOnInit(): void {
    this.loadCategories();
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
