import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { AppComponent } from './app.component';
import { SessionService } from './services/session.service';
import { Router } from '@angular/router';
import {Observable, of} from "rxjs";


describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('devrait afficher le titre', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('mat-toolbar span')?.textContent).toContain('Yoga app');
  });

  it('devrait afficher les liens Login/Register quand déconnecté', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain('Login');
    expect(compiled.textContent).toContain('Register');
  });

  it('devrait appeler logout et naviguer', () => {
    const sessionMock = { logOut: jest.fn() };
    const routerMock = { navigate: jest.fn() };
    const fixture = TestBed.overrideProvider(SessionService, { useValue: sessionMock })
      .overrideProvider(Router, { useValue: routerMock })
      .createComponent(AppComponent);
    const component = fixture.componentInstance;
    component.logout();
    expect(sessionMock.logOut).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['']);
  });

  it('devrait afficher Sessions/Account/Logout quand connecté', () => {
    const sessionMock = {
      $isLogged: (): Observable<boolean> => of(true)
    };

    const fixture = TestBed.overrideProvider(SessionService, { useValue: sessionMock })
      .createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain('Sessions');
    expect(compiled.textContent).toContain('Account');
    expect(compiled.textContent).toContain('Logout');
    expect(compiled.textContent).not.toContain('Login');
  });
});
