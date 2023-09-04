import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material.module';
import { UsersService } from './services/users.service';

@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    MaterialModule
  ],
  providers: [
    UsersService
  ]
})
export class SharedModule { }
