import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { Session } from '../interfaces/session.interface';
import { SessionApiService } from './session-api.service';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it("devrait retourner un Observable<Session[]> pour all()", () => {
    const mockSessions: Session[] = [
      { id: 1, name: 'Session de Yoga', description: 'Description', date: new Date('2025-09-27'), teacher_id: 1, users: [] } as Session
    ];

    service.all().subscribe((sessions) => {
      expect(sessions).toEqual(mockSessions);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush(mockSessions);
  });

  it('devrait retourner un Observable<Session> pour detail()', () => {
    const mockSession: Session = { id: 1, name: 'Session de Yoga', description: 'Description', date: new Date('2025-09-27'), teacher_id: 1, users: [] } as Session;

    service.detail('1').subscribe((session) => {
      expect(session).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession);
  });

  it('devrait retourner un Observable<Session> pour create()', () => {
    const newSession: Session = { name: 'Nouvelle Session', description: 'Description', date: new Date('2025-09-27'), teacher_id: 1, users: [] } as Session;
    const createdSession: Session = { id: 1, ...newSession } as Session;

    service.create(newSession).subscribe((session) => {
      expect(session).toEqual(createdSession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newSession);
    req.flush(createdSession);
  });

  it('devrait retourner un Observable<Session> pour update()', () => {
    const updatedSession: Session = { id: 1, name: 'Session Modifiée', description: 'Description', date: new Date('2025-09-27'), teacher_id: 1, users: [] } as Session;

    service.update('1', updatedSession).subscribe((session) => {
      expect(session).toEqual(updatedSession);
    });

    const req = httpMock.expectOne('api/session/1');

    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedSession);

    req.flush(updatedSession);
  });

  it('devrait retourner un Observable<any> pour delete()', () => {
    service.delete('1').subscribe((result) => {
      expect(result).toBeTruthy();
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('devrait retourner un Observable<void> pour participate()', () => {
    service.participate('1', '2').subscribe((result) => {
      expect(result).toBeUndefined();
    });

    const req = httpMock.expectOne('api/session/1/participate/2');
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('devrait retourner un Observable<void> pour unParticipate()', () => {
    service.unParticipate('1', '2').subscribe((result) => {
      expect(result).toBeUndefined();
    });

    const req = httpMock.expectOne('api/session/1/participate/2');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
