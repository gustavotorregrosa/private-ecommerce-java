import { ICategory } from "./ICategory";

export interface IProduct{
    id: string;
    name: string;
    category: ICategory;
    description?: string;
}