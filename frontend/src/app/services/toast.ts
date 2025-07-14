import { Injectable } from '@angular/core';
import Toastify from 'toastify-js'

@Injectable({
    providedIn: 'root'
})
export class ToastService {

    public showSuccess(message: string): void {
        Toastify({
            text: message,
            duration: 3000,
            close: true,
            gravity: 'top',
            position: 'right',
            backgroundColor: 'linear-gradient(to right, #00b09b, #96c93d)',
        }).showToast();
    }

    public showError(message: string): void {
        Toastify({
            text: message,
            duration: 3000,
            close: true,
            gravity: 'top',
            position: 'right',
            backgroundColor: 'linear-gradient(to right, #ff5f6d, #ffc371)',
        }).showToast();
    }
}