import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-author-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './component.html',
  styleUrls: ['./component.css']
})
export class AuthorCreateComponent {
  authorForm: FormGroup;
  isLoading = false;
  message = '';

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.authorForm = this.fb.group({
      name: ['', Validators.required],
      nationality: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.authorForm.invalid) return;

    this.isLoading = true;
    this.message = '';

    // CORREÇÃO: Caminho relativo
    const url = '/api/v1/authors';

    this.http.post(url, this.authorForm.value).subscribe({
      next: () => {
        this.message = 'Autor cadastrado com sucesso!';
        this.authorForm.reset();
        this.isLoading = false;
      },
      error: (err) => {
        this.message = 'Erro ao cadastrar autor: ' + (err.error?.message || 'Erro desconhecido');
        this.isLoading = false;
      }
    });
  }
}
