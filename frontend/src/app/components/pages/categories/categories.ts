import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../../services/httpService';

@Component({
  selector: 'app-categories',
  imports: [],
  templateUrl: './categories.html',
  styleUrl: './categories.scss'
})
export class Categories implements OnInit {

  public categories: any[] = [];

  constructor(private httpService: HttpService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  private loadCategories(): void {
   this.httpService.get<any[]>('/categories')
      .then(categories => {
        this.categories = categories;
      })
      .catch(error => {
        console.error('Error loading categories:', error);
      })
  }

}
