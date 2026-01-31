import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './component.html',
  styleUrls: ['./component.css']
})
export class DashboardComponent {
  // Dados simulados para o dashboard (futuramente virão da API)
  stats = [
    { title: 'Usuários Ativos', value: '124', icon: 'group', color: 'blue' },
    { title: 'Livros Cadastrados', value: '856', icon: 'library_books', color: 'green' },
    { title: 'Empréstimos', value: '42', icon: 'bookmark_added', color: 'orange' },
    { title: 'Pendências', value: '5', icon: 'warning', color: 'red' }
  ];

  recentActivities = [
    { user: 'João Silva', action: 'Cadastrou o livro "Clean Code"', time: '2 min atrás' },
    { user: 'Maria Souza', action: 'Atualizou perfil de cliente', time: '15 min atrás' },
    { user: 'Admin', action: 'Realizou backup do sistema', time: '1 hora atrás' }
  ];
}
