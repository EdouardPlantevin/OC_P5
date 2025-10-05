import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { JwtInterceptor } from './jwt.interceptor';
import { SessionService } from '../services/session.service';

describe('JwtInterceptor', () => {
  let http: HttpClient;
  let httpMock: HttpTestingController;
  let sessionMock: any;

  beforeEach(() => {
    sessionMock = { isLogged: true, sessionInformation: { token: 'token_de_test' } };
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: SessionService, useValue: sessionMock },
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
      ],
    });
    http = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('devrait ajouter le header Authorization si connecté', () => {
    http.get('/sessions').subscribe();
    const req = httpMock.expectOne('/sessions');
    expect(req.request.headers.get('Authorization')).toBe('Bearer token_de_test');
    req.flush({});
  });

  it('ne devrait pas ajouter Authorization si non connecté', () => {
    sessionMock.isLogged = false;
    http.get('/sessions').subscribe();
    const req = httpMock.expectOne('/sessions');
    expect(req.request.headers.has('Authorization')).toBe(false);
    req.flush({});
  });
});


