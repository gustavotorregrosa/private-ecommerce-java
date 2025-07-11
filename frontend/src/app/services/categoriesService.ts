import { Injectable } from "@angular/core";
import { HttpService } from "./httpService";
import { ICategory } from "../interfaces/ICategory";


@Injectable({
    providedIn: 'root'
})
export class CategoriesService {

    constructor(private httpService: HttpService) {}

    private endpoint: string = '/categories';

    public async getAll(): Promise<ICategory[]> {
        const response = await this.httpService.get<ICategory[]>(this.endpoint);
        return response;
    }

    public async getById(categoryId: string): Promise<ICategory> {
        const response = await this.httpService.get<ICategory>(`${this.endpoint}/${categoryId}`);
        return response;
    }

    public async create(category: ICategory): Promise<ICategory> {
        const response = await this.httpService.post<ICategory>(this.endpoint, category as any);
        return response;
    }
    
    public async update(category: ICategory): Promise<ICategory> {
        const response = await this.httpService.put<ICategory>(`/categories/${category.id}`, category as any);
        return response;
    }
    
    public async delete(categoryId: string): Promise<void> {
        await this.httpService.delete(`/categories/${categoryId}`);
    }
}