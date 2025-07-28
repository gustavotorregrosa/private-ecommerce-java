import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { IModalData } from '../main/movimentations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MovimentationsService } from '../../../../services/movimentationsService';

interface IMovimentation{
  productId: string;
  amount: number;
}

@Component({
  selector: 'app-add-movimentation',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, FormsModule, MatDialogModule],
  templateUrl: './add-movimentation.html',
  styleUrl: './add-movimentation.scss'
})
export class AddMovimentationModal {

  public productID: string | null = null;
  public action: 'Add' | 'Withdraw' = 'Add';
  public amount: number = 0;

  constructor(@Inject(MAT_DIALOG_DATA) private modalData: IModalData, private movimentationService: MovimentationsService) {
    this.productID = modalData.productId;
    this.amount = 1;
    this.action = modalData.action === 'add' ? 'Add' : 'Withdraw';
  }

  public async addMovimentation(): Promise<void> {

    const movimentation: IMovimentation = {
      productId: this.productID!,
      amount:  this.action == 'Add' ? this.amount : -this.amount
    };

    await this.movimentationService.addMovimentation(movimentation)

  }

  

}
