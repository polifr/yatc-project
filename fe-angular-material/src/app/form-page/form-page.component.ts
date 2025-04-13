import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-form-page',
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    MatCardModule, 
    MatButtonModule, 
    MatInputModule
  ],
  templateUrl: './form-page.component.html',
  styleUrl: './form-page.component.scss'
})
export class FormPageComponent {
  form: FormGroup;
  responseData: any;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.form = this.fb.group({
      textInput: ['']
    });
  }

  submitForm() {
    const message = this.form.value.textInput;
    const requestBody = { messageBody: message };

    this.http.post<any>('/api/spring-servlet/message/v1', requestBody)
      .subscribe({
        next: data => this.responseData = data,
        error: err => console.error('Errore nella richiesta:', err)
      });
  }
}
