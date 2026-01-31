import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-client-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './component.html',
  styleUrls: ['./component.css']
})
export class ClientCreateComponent {
  clientForm: FormGroup;
  isLoading = false;
  message = '';

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.clientForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.clientForm.invalid) return;

    this.isLoading = true;
    this.message = '';

    // CORREÇÃO: Caminho relativo
    const url = '/api/v1/clients';

    this.http.post(url, this.clientForm.value).subscribe({
      next: () => {
        this.message = 'Cliente cadastrado com sucesso!';
        this.clientForm.reset();
        this.isLoading = false;
      },
      error: (err) => {
        this.message = 'Erro ao cadastrar cliente: ' + (err.error?.message || 'Erro desconhecido');
        this.isLoading = false;
      }
    });
  }
}
