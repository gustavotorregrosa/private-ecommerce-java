import { Subject } from 'rxjs';;

export const refreshCategoriesObservable = new Subject<void>(); 

export const refreshProductsObservable  = new Subject<void>();

export const refreshMovimentationsObservable = new Subject<string>();