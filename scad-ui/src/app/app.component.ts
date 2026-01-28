import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // SIMULAÇÃO DO USUÁRIO LOGADO (Mude para 'GERENTE' ou 'USUARIO' para testar)
  userRole: string = 'ADMIN';

  // Lógica para controlar o menu acordeão
  public openMenu: string | null = null;

  get isAdmin() { return this.userRole === 'ADMIN'; }
  get isManager() { return this.userRole === 'GERENTE' || this.userRole === 'ADMIN'; }

  toggleMenu(menuName: string) {
    if (this.openMenu === menuName) {
      this.openMenu = null; // Fecha o menu se já estiver aberto
    } else {
      this.openMenu = menuName; // Abre o novo menu
    }
  }

  logout() {
    console.log('Saindo...');
  }
}