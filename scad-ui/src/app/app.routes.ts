import { Routes } from '@angular/router';
import { UserCreateComponent } from './user-create/component';
import { BookSearchComponent } from './book-search/component';
import { AuthorSearchComponent } from './author-search/component';
import { BookCreateComponent } from './book-create/component';
import { AuthorCreateComponent } from './author-create/component';
import { ClientCreateComponent } from './client-create/component';
import { LoginComponent } from './login/component';
import { DashboardComponent } from './dashboard/component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'dashboard', component: DashboardComponent }, // Nova Home
    { path: 'cadastro-usuario', component: UserCreateComponent },
    { path: 'pesquisar-livros', component: BookSearchComponent },
    { path: 'pesquisar-autor', component: AuthorSearchComponent },
    { path: 'cadastro-livro', component: BookCreateComponent },
    { path: 'cadastro-autor', component: AuthorCreateComponent },
    { path: 'cadastro-cliente', component: ClientCreateComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];
