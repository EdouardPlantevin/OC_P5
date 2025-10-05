import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { LoginComponent } from './login.component';
import { of, throwError } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { Router } from '@angular/router';
import {SessionInformation} from "../../../../interfaces/sessionInformation.interface";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  let authMock: jest.Mocked<AuthService>;
  let sessionMock: jest.Mocked<SessionService>;
  let routerMock: jest.Mocked<Router>;

  beforeEach(async () => {
    authMock = { login: jest.fn() } as unknown as jest.Mocked<AuthService>;
    sessionMock = { logIn: jest.fn() } as unknown as jest.Mocked<SessionService>;
    routerMock = { navigate: jest.fn() } as unknown as jest.Mocked<Router>;
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: SessionService, useValue: sessionMock },
        { provide: AuthService, useValue: authMock },
        { provide: Router, useValue: routerMock }
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('devrait invalider le formulaire quand vide', () => {
    expect(component.form.invalid).toBe(true);
  });

  it('devrait valider le formulaire quand rempli', () => {
    component.form.setValue({ email: 'test@test.com', password: 'password' });
    expect(component.form.valid).toBe(true);
  });

  it('devrait appeler AuthService et naviguer au succès', () => {
    const response = { id: 1, admin: true } as Partial<SessionInformation> as SessionInformation;
    authMock.login.mockReturnValue(of(response));
    component.form.setValue({ email: 'test@test.com', password: 'password' });
    component.submit();
    expect(authMock.login).toHaveBeenCalled();
    expect(sessionMock.logIn).toHaveBeenCalledWith(response);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('devrait mettre onError à true en cas d’erreur', () => {
    authMock.login.mockReturnValue(throwError(() => new Error('bad')));
    component.form.setValue({ email: 'test@test.com', password: 'password' });
    component.submit();
    expect(component.onError).toBe(true);
  });
});
