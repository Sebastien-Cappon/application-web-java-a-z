import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material.module';
import { UsersService } from './services/users.service';
import { UsersResolver } from './resolvers/users.resolver';

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
    UsersService,
    UsersResolver
  ]
})
export class SharedModule { }
