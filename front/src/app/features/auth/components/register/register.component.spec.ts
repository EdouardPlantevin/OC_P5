import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { of, throwError } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  let authMock: jest.Mocked<AuthService>;
  let routerMock: jest.Mocked<Router>;

  beforeEach(async () => {
    authMock = { register: jest.fn() } as unknown as jest.Mocked<AuthService>;
    routerMock = { navigate: jest.fn() } as unknown as jest.Mocked<Router>;
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: AuthService, useValue: authMock },
        { provide: Router, useValue: routerMock }
      ],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('devrait appeler register et naviguer vers /login', () => {
    authMock.register.mockReturnValue(of(void 0));
    component.form.setValue({
      email: 'test@test.com', firstName: 'jonh', lastName: 'doe', password: 'password'
    });
    component.submit();
    expect(authMock.register).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('devrait mettre onError à true en cas d’erreur', () => {
    authMock.register.mockReturnValue(throwError(() => new Error('bad')));
    component.form.setValue({
      email: 'test@test.com', firstName: 'jonh', lastName: 'doe', password: 'password'
    });
    component.submit();
    expect(component.onError).toBe(true);
  });

  it('devrait invalider le formulaire si champs vides', () => {
    component.form.setValue({ email: '', firstName: '', lastName: '', password: '' });
    expect(component.form.invalid).toBe(true);
  });
});
