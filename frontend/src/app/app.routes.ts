import { Routes } from '@angular/router';
import { Home } from './components/pages/home/home';
import { Categories } from './components/pages/categories/categories';

export const routes: Routes = [

    {    
        path: '',    
        component: Home,  
    },

    {
      path: 'categories',
      component: Categories
    }

];
