import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { StockList } from './stock-list.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<StockList>;

@Injectable()
export class StockListService {

    private resourceUrl =  SERVER_API_URL + 'api/stock-lists';

    constructor(private http: HttpClient) { }

    create(stockList: StockList): Observable<EntityResponseType> {
        const copy = this.convert(stockList);
        return this.http.post<StockList>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(stockList: StockList): Observable<EntityResponseType> {
        const copy = this.convert(stockList);
        return this.http.put<StockList>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<StockList>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<StockList[]>> {
        const options = createRequestOption(req);
        return this.http.get<StockList[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<StockList[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: StockList = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<StockList[]>): HttpResponse<StockList[]> {
        const jsonResponse: StockList[] = res.body;
        const body: StockList[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to StockList.
     */
    private convertItemFromServer(stockList: StockList): StockList {
        const copy: StockList = Object.assign({}, stockList);
        return copy;
    }

    /**
     * Convert a StockList to a JSON which can be sent to the server.
     */
    private convert(stockList: StockList): StockList {
        const copy: StockList = Object.assign({}, stockList);
        return copy;
    }
}
