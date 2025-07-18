import { Injectable } from "@angular/core";
import { HttpService } from "./httpService";
import { IProduct } from "../interfaces/IProduct";


@Injectable({
    providedIn: 'root'
})
export class ProductsService {

    constructor(private httpService: HttpService) {}

    private endpoint: string = '/products';

    private mapToDTO(product: IProduct): any {
        return {
            id: product.id,
            name: product.name,
            categoryId: product.category.id 
        };
    }

    public async getAll(): Promise<IProduct[]> {
        const response = await this.httpService.get<IProduct[]>(this.endpoint);
        return response;
    }

    public async getById(productId: string): Promise<IProduct> {
        const response = await this.httpService.get<IProduct>(`${this.endpoint}/${productId}`);
        return response;
    }

    public async create(product: IProduct): Promise<IProduct> {
        const response = await this.httpService.post<IProduct>(this.endpoint, this.mapToDTO(product));
        return response;
    }
    
    public async update(product: IProduct): Promise<IProduct> {
        const response = await this.httpService.put<IProduct>(`${this.endpoint}/${product.id}`, this.mapToDTO(product));
        return response;
    }
    
    public async delete(productId: string): Promise<void> {
        await this.httpService.delete(`${this.endpoint}/${productId}`);
    }
}