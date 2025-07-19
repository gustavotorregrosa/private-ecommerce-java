import { Injectable } from "@angular/core";
import { HttpService } from "./httpService";
import { ICategory } from "../interfaces/ICategory";
import { IResponse } from "../interfaces/IResponse";


@Injectable({
    providedIn: 'root'
})
export class CategoriesService {

    constructor(private httpService: HttpService) {}

    private endpoint: string = '/categories';

    public async getAll(): Promise<ICategory[]> {
        const response = await this.httpService.get<IResponse<ICategory[]>>(this.endpoint);
        return response.data;
    }

    public async getById(categoryId: string): Promise<ICategory> {
        const response = await this.httpService.get<IResponse<ICategory>>(`${this.endpoint}/${categoryId}`);
        return response.data;
    }

    public async create(category: ICategory): Promise<ICategory> {
        const response = await this.httpService.post<IResponse<ICategory>>(this.endpoint, category as any);
        return response.data;
    }
    
    public async update(category: ICategory): Promise<ICategory> {
        const response = await this.httpService.put<IResponse<ICategory>>(`${this.endpoint}/${category.id}`, category as any);
        return response.data;
    }
    
    public async delete(categoryId: string): Promise<void> {
        await this.httpService.delete(`${this.endpoint}/${categoryId}`);
    }
}