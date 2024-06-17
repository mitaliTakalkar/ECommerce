import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { UserStorageService } from '../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
loginForm!:FormGroup;
hidePassword=true;
constructor(
  private formBuilder:FormBuilder,
  private authService:AuthService,
  private snackBar:MatSnackBar,
  private router:Router
){}
ngOnInit():void{
  this.loginForm=this.formBuilder.group({
    email:[null,[Validators.required, Validators.email]],
    password:[null,[Validators.required]],
  })
}
togglePasswordVisibility(){
  this.hidePassword=!this.hidePassword;
}
onSubmit():void{
  const username=this.loginForm.get('email')!.value;
  const password=this.loginForm.get('password')!.value;
  this.authService.login(username,password).subscribe(
    (res)=>{

      if( UserStorageService.isAdminLoggedIn()){
        localStorage.setItem('role','admin');
        this.router.navigateByUrl('admin/dashboard');
      }else if( UserStorageService.isCustomerLoggedIn()){
        localStorage.setItem('role','user');
        this.router.navigateByUrl('customer/dashboard');
      // this.snackBar.open('Login success', 'Ok', { duration: 5000 });
      }
      
    },
    (error)=>{
      this.snackBar.open('Bad credentials', 'ERROR', { duration: 5000 });
    }
  )
}

}
