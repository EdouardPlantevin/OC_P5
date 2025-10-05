import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import { Teacher } from '../interfaces/teacher.interface';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    service = TestBed.inject(TeacherService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it("devrait appeler GET api/teacher (all)", () => {
    const mock: Teacher[] = [{ id: 1, firstName: 'Edouard', lastName: 'Plantevin' } as Teacher];
    service.all().subscribe((response) => expect(response).toEqual(mock));
    const req = httpMock.expectOne('api/teacher');
    expect(req.request.method).toBe('GET');
    req.flush(mock);
  });

  it('devrait appeler GET api/teacher/:id (detail)', () => {
    const mock = { id: 1 } as Teacher;
    service.detail('1').subscribe((response) => expect(response).toEqual(mock));
    const req = httpMock.expectOne('api/teacher/1');
    expect(req.request.method).toBe('GET');
    req.flush(mock);
  });
});
