import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import {BooksComponent} from './books/books.component';

const appRoutes: Routes = [
	{
		path: '',
		redirectTo: 'app-books/all',
		pathMatch: 'full'
	},
	{
		path: 'app-register',
		component: RegisterComponent
	},
	{
		path: 'app-login',
		component: LoginComponent
	},
  {
    path: 'app-books/:genre',
    component: BooksComponent
  }
]

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
