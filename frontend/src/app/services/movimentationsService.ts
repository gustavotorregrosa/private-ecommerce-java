import { Injectable } from "@angular/core";
import { IMovimentation, IStockPosition } from "../interfaces/IStockPosition";
import { HttpService } from "./httpService";
import { IResponse } from "../interfaces/IResponse";

@Injectable({
    providedIn: 'root'
})
export class MovimentationsService {

    constructor(private httpService: HttpService) {}

    private endpoint: string = '/movimentations';

    public async addMovimentation(movimentation: IMovimentation): Promise<void> {
        const movimentationObj = {...movimentation} as any;
        const response = await this.httpService.post<IResponse<void>>(this.endpoint, movimentationObj);
        return response.data;
    }

    

    public async getStockPositionsByProductId(productId: string): Promise<IStockPosition[]> {

        return [
            { amout: 10, date: new Date('2023-01-01') },
            { amout: 20, date: new Date('2023-01-02') },
            { amout: -15, date: new Date('2023-01-03') },
            { amout: 30, date: new Date('2023-01-04') },

        ];
    }
}