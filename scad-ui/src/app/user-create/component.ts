import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-user-create',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './component.html', // CORRIGIDO: Removido o prefixo user-create.
    styleUrls: ['./component.css']   // CORRIGIDO: Removido o prefixo user-create.
})
export class UserCreateComponent {
    userForm: FormGroup;
    isLoading = false;
    message = '';

    // Perfis disponíveis
    roles = [
        { value: 'ADMIN', label: 'Administrador (Acesso Total)' },
        { value: 'GERENTE', label: 'Gerente (Gestão de Livros/Autores)' },
        { value: 'USUARIO', label: 'Usuário Padrão (Apenas Consulta)' }
    ];

    constructor(private fb: FormBuilder, private http: HttpClient) {
        this.userForm = this.fb.group({
            username: ['', [Validators.required, Validators.minLength(3)]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(6)]],
            role: ['USUARIO', Validators.required] // Padrão: Usuário
        });
    }

    onSubmit() {
        if (this.userForm.invalid) return;

        this.isLoading = true;
        this.message = '';

        // Ajuste a URL para o seu backend
        const url = 'http://localhost:8080/api/v1/users';

        // Aqui você precisaria adicionar o Header Authorization com o token
        // Mas para o exemplo visual, vamos focar no envio
        this.http.post(url, this.userForm.value).subscribe({
            next: () => {
                this.message = 'Usuário cadastrado com sucesso!';
                this.userForm.reset({ role: 'USUARIO' });
                this.isLoading = false;
            },
            error: (err) => {
                this.message = 'Erro ao cadastrar: ' + (err.error?.message || 'Erro desconhecido');
                this.isLoading = false;
            }
        });
    }
}
