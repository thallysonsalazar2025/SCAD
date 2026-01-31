import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-book-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './component.html',
  styleUrls: ['./component.css']
})
export class BookCreateComponent {
  bookForm: FormGroup;
  isLoading = false;
  message = '';

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.bookForm = this.fb.group({
      title: ['', Validators.required],
      isbn: ['', Validators.required],
      authorId: ['', Validators.required],
      genre: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.bookForm.invalid) return;

    this.isLoading = true;
    this.message = '';

    // CORREÇÃO: Caminho relativo
    const url = '/api/v1/books';

    this.http.post(url, this.bookForm.value).subscribe({
      next: () => {
        this.message = 'Livro cadastrado com sucesso!';
        this.bookForm.reset();
        this.isLoading = false;
      },
      error: (err) => {
        this.message = 'Erro ao cadastrar livro: ' + (err.error?.message || 'Erro desconhecido');
        this.isLoading = false;
      }
    });
  }
}
