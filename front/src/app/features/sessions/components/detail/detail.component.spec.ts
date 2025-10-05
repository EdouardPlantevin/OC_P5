import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from '../../../../services/session.service';
import { DetailComponent } from './detail.component';
import { of } from 'rxjs';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from 'src/app/services/teacher.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let sessionApiSpy: any;
  let teacherServiceSpy: any;
  let routerMock: any;
  let matSnackBarMock: any;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  };

  beforeEach(async () => {
    sessionApiSpy = { detail: jest.fn(), delete: jest.fn() };
    teacherServiceSpy = { detail: jest.fn() };
    routerMock = { navigate: jest.fn() };
    matSnackBarMock = { open: jest.fn() };

    sessionApiSpy.detail.mockReturnValue(of({
      id: 1,
      name: 'Test Session',
      description: 'Test Description',
      date: new Date(),
      teacher_id: 3,
      users: [1, 2, 3]
    }));
    teacherServiceSpy.detail.mockReturnValue(of({
      id: 3,
      lastName: 'Doe',
      firstName: 'John'
    }));

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        ReactiveFormsModule,
        NoopAnimationsModule
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: sessionApiSpy },
        { provide: TeacherService, useValue: teacherServiceSpy },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: { get: () => '1' } } } }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('devrait afficher correctement les informations de la session', () => {
    const mockSession = {
      id: 1,
      name: 'Yoga Session',
      description: 'Session de yoga relaxante',
      date: new Date('2025-09-27'),
      teacher_id: 3,
      users: [1, 2, 3]
    };
    const mockTeacher = {
      id: 3,
      lastName: 'Plantevin',
      firstName: 'Edouard'
    };

    sessionApiSpy.detail.mockReturnValue(of(mockSession));
    teacherServiceSpy.detail.mockReturnValue(of(mockTeacher));

    component.ngOnInit();

    expect(sessionApiSpy.detail).toHaveBeenCalledWith('1');
    expect(teacherServiceSpy.detail).toHaveBeenCalledWith('3');
    expect(component.session).toEqual(mockSession);
    expect(component.teacher).toEqual(mockTeacher);
  });

  it('devrait supprimer correctement la session', fakeAsync(() => {
    sessionApiSpy.delete.mockReturnValue(of({}));

    component.delete();
    tick();

    expect(sessionApiSpy.delete).toHaveBeenCalledWith('1');
    expect(matSnackBarMock.open).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 });
    expect(routerMock.navigate).toHaveBeenCalledWith(['sessions']);
  }));

  it('devrait afficher le bouton Delete si l\'utilisateur connecté est un admin', () => {
    expect(component.isAdmin).toBe(true);
  });

  it('devrait appeler window.history.back via back()', () => {
    const spy = jest.spyOn(window.history, 'back');
    component.back();
    expect(spy).toHaveBeenCalled();
  });

  it('devrait appeler participate puis refetch', () => {
    sessionApiSpy.participate = jest.fn().mockReturnValue(of({}));
    const fetchSessionSpy = jest.spyOn(component as any, 'fetchSession');
    
    component.participate();
    
    expect(sessionApiSpy.participate).toHaveBeenCalledWith('1', '1');
    expect(fetchSessionSpy).toHaveBeenCalled();
  });
});
