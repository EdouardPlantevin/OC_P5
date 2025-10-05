import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { take } from 'rxjs/operators';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('devrait mettre à jour sessionInformation et isLogged lors de logIn()', () => {
    const user = { id: 1, admin: true } as SessionInformation;

    service.logIn(user);

    expect(service.sessionInformation).toEqual(user);
    expect(service.isLogged).toBe(true);
  });

  it('devrait reset sessionInformation et isLogged lors de logOut()', () => {
    const user = { id: 1, admin: true } as SessionInformation;
    service.logIn(user);

    service.logOut();

    expect(service.sessionInformation).toBeUndefined();
    expect(service.isLogged).toBe(false);
  });

});
