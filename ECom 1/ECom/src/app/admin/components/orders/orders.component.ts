import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss'
})
export class OrdersComponent {

  orders: any;
  constructor(private adminService: AdminService,
    private snackBar: MatSnackBar){
}

ngOnInit(){
  this.getPlacedOrders();
}
    getPlacedOrders(){
      this.adminService.getPlacedOrders().subscribe(res =>{
        this.orders=res;
      })
    }

    changeOrderStatus(orderId: number,status: string){
      this.adminService.changeOrderStatus(orderId,status).subscribe(res => {
        if(res.id != null){
          this.snackBar.open("Order Status changed successfully", "Close",{duration: 5000});
          this.getPlacedOrders();
        }else{
          this.snackBar.open("Something went wrong","Close",{duration: 5000});
        }
      })
    }
}
