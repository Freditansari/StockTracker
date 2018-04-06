import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Portfolio } from './portfolio.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Portfolio>;

@Injectable()
export class PortfolioService {

    private resourceUrl =  SERVER_API_URL + 'api/portfolios';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(portfolio: Portfolio): Observable<EntityResponseType> {
        const copy = this.convert(portfolio);
        return this.http.post<Portfolio>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(portfolio: Portfolio): Observable<EntityResponseType> {
        const copy = this.convert(portfolio);
        return this.http.put<Portfolio>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Portfolio>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Portfolio[]>> {
        const options = createRequestOption(req);
        return this.http.get<Portfolio[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Portfolio[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Portfolio = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Portfolio[]>): HttpResponse<Portfolio[]> {
        const jsonResponse: Portfolio[] = res.body;
        const body: Portfolio[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Portfolio.
     */
    private convertItemFromServer(portfolio: Portfolio): Portfolio {
        const copy: Portfolio = Object.assign({}, portfolio);
        copy.purchaseDate = this.dateUtils
            .convertDateTimeFromServer(portfolio.purchaseDate);
        copy.lastUpdated = this.dateUtils
            .convertDateTimeFromServer(portfolio.lastUpdated);
        return copy;
    }

    /**
     * Convert a Portfolio to a JSON which can be sent to the server.
     */
    private convert(portfolio: Portfolio): Portfolio {
        const copy: Portfolio = Object.assign({}, portfolio);

        copy.purchaseDate = this.dateUtils.toDate(portfolio.purchaseDate);

        copy.lastUpdated = this.dateUtils.toDate(portfolio.lastUpdated);
        return copy;
    }
}
