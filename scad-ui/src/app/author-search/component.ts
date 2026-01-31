import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-author-search',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <h2>Pesquisar Autores</h2>
      <p>Funcionalidade de pesquisa de autores em desenvolvimento.</p>
    </div>
  `,
  styles: [`
    .container { padding: 20px; }
    h2 { color: var(--baby-blue-text); }
  `]
})
export class AuthorSearchComponent {}
