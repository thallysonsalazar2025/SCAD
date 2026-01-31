import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive, Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  userRole: string = 'ADMIN';
  userName: string = 'Admin User';
  public openMenu: string | null = null;

  // Controle de visibilidade do layout
  showSidebar: boolean = true;

  constructor(private router: Router) {
    // Monitora mudanças de rota para esconder a sidebar no login
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.showSidebar = !event.url.includes('/login');
    });
  }

  get isAdmin() { return this.userRole === 'ADMIN'; }
  get isManager() { return this.userRole === 'GERENTE' || this.userRole === 'ADMIN'; }

  toggleMenu(menuName: string) {
    if (this.openMenu === menuName) {
      this.openMenu = null;
    } else {
      this.openMenu = menuName;
    }
  }

  logout() {
    console.log('Saindo...');
    this.router.navigate(['/login']);
  }
}
