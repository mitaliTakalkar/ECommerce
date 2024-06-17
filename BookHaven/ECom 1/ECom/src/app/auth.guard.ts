import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  var r = localStorage.getItem('role');
  if(r === 'admin'){
    return true;
  }
  else if(r === 'user'){
    return true;
  }
  else{
    router.navigateByUrl('login')
    return false;
  }
};
