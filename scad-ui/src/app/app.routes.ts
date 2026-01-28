import { Routes } from '@angular/router';
import { UserCreateComponent } from './user-create/component'; // CORRIGIDO: Caminho do arquivo

export const routes: Routes = [
    { path: 'cadastro-usuario', component: UserCreateComponent },
    // ... outras rotas
];