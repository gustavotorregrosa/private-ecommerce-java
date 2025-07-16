import { Routes } from '@angular/router';
import { Home } from './components/pages/home/home';
import { Categories } from './components/pages/categories/main/categories';
import { isAuthGuard } from './misc/guards';
import { Products } from './components/pages/products/main/products';

export const routes: Routes = [

    {    
        path: '',    
        component: Home,  
    },

    {
      path: 'categories',
      component: Categories,
      canActivate: [isAuthGuard]
    },

    {
      path: 'products',
      component: Products,
      canActivate: [isAuthGuard]
    }

];
