import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from '../../../../services/session.service';
import { FormComponent } from './form.component';
import { of } from 'rxjs';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from 'src/app/services/teacher.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
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

  const mockSession = {
    id: 1,
    name: 'Session de Test',
    description: 'Description de Test',
    date: new Date('2025-09-27'),
    teacher_id: 3,
    users: []
  };

  beforeEach(async () => {
    sessionApiSpy = {
      create: jest.fn().mockReturnValue(of(mockSession)),
      update: jest.fn().mockReturnValue(of(mockSession)),
      detail: jest.fn().mockReturnValue(of(mockSession))
    };
    teacherServiceSpy = { all: jest.fn().mockReturnValue(of([])) };
    routerMock = {
      navigate: jest.fn(),
      url: '/sessions'
    };
    matSnackBarMock = { open: jest.fn() };

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        ReactiveFormsModule,
        NoopAnimationsModule
      ],
      declarations: [FormComponent],
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

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('devrait créer une session', fakeAsync(() => {
    routerMock.url = '/sessions/create';
    component.ngOnInit();

    component.sessionForm?.patchValue({
      name: 'Nouvelle Session',
      description: 'Nouvelle Description',
      date: '2025-09-27',
      teacher_id: 1
    });

    component.submit();
    tick();

    expect(sessionApiSpy.create).toHaveBeenCalled();
    expect(matSnackBarMock.open).toHaveBeenCalledWith('Session created !', 'Close', { duration: 3000 });
    expect(routerMock.navigate).toHaveBeenCalledWith(['sessions']);
  }));

  it("devrait afficher une erreur en l'absence d'un champ obligatoire lors de la création", () => {
    routerMock.url = '/sessions/create';
    component.ngOnInit();

    component.sessionForm?.patchValue({
      name: '',
      description: '',
      date: '',
      teacher_id: ''
    });

    expect(component.sessionForm?.invalid).toBe(true);
    expect(component.sessionForm?.get('name')?.hasError('required')).toBe(true);
    expect(component.sessionForm?.get('description')?.hasError('required')).toBe(true);
    expect(component.sessionForm?.get('date')?.hasError('required')).toBe(true);
    expect(component.sessionForm?.get('teacher_id')?.hasError('required')).toBe(true);
  });

  it('devrait modifier une session', fakeAsync(() => {
    routerMock.url = '/sessions/update/1';
    component.ngOnInit();

    component.sessionForm?.patchValue({
      name: 'Session Modifiée',
      description: 'Description Modifiée',
      date: '2025-09-27',
      teacher_id: 2
    });

    component.submit();
    tick();

    expect(sessionApiSpy.update).toHaveBeenCalledWith('1', expect.any(Object));
    expect(matSnackBarMock.open).toHaveBeenCalledWith('Session updated !', 'Close', { duration: 3000 });
    expect(routerMock.navigate).toHaveBeenCalledWith(['sessions']);
  }));

  it("devrait afficher une erreur en l'absence d'un champ obligatoire lors de la modification", () => {
    routerMock.url = '/sessions/update/1';
    component.ngOnInit();

    component.sessionForm?.patchValue({
      name: '',
      description: '',
      date: '',
      teacher_id: ''
    });

    expect(component.sessionForm?.invalid).toBe(true);
    expect(component.sessionForm?.get('name')?.hasError('required')).toBe(true);
    expect(component.sessionForm?.get('description')?.hasError('required')).toBe(true);
    expect(component.sessionForm?.get('date')?.hasError('required')).toBe(true);
    expect(component.sessionForm?.get('teacher_id')?.hasError('required')).toBe(true);
  });
});
