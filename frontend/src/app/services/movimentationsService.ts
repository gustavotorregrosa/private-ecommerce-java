import { Injectable } from "@angular/core";
import { IMovimentation } from "../interfaces/IMovimentation";

@Injectable({
    providedIn: 'root'
})
export class MovimentationsService {

    public async getMovimentationsByProductId(productId: string): Promise<IMovimentation[]> {
        
        return [
            { amout: 10, date: new Date('2023-01-01') },
            { amout: 20, date: new Date('2023-01-02') },
            { amout: -15, date: new Date('2023-01-03') },
            { amout: 30, date: new Date('2023-01-04') },

        ];
    }
}