import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-book-search',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <h2>Pesquisar Livros</h2>
      <p>Funcionalidade de pesquisa de livros em desenvolvimento.</p>
    </div>
  `,
  styles: [`
    .container { padding: 20px; }
    h2 { color: var(--baby-blue-text); }
  `]
})
export class BookSearchComponent {}
